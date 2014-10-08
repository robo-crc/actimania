package com.framework.helpers;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.joda.time.DateTime;

import com.framework.helpers.Database.DatabaseType;
import com.framework.models.User;

public class JongoJdbcRealm extends AuthorizingRealm
{
	public JongoJdbcRealm() 
	{
		super();
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException 
	{
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		
		// Null username is invalid
		if (username == null)
		{
			throw new AccountException("Null usernames are not allowed by this realm.");
		}

		// Find a better way to select current database
		Database database = new Database(DatabaseType.PRODUCTION);
		
		SimpleAuthenticationInfo info = null;
		try 
		{
			User user = database.findOne(User.class, "{ email : # }", username);
			
			if (user == null || user.isDeleted)
			{
				throw new AccountException("Invalid user or password.");
			}
			else if(user.isLocked)
			{
				database.save(new User(user._id, user.email, user.password, user.roles, user.unsuccessfullAttemptsCount + 1, user.isLocked, user.lockedTime, user.isDeleted));

				throw new LockedAccountException("Your account is locked. Please contact your administrator.");
			}
			else if(!user.password.equals(User.encryptPassword(String.valueOf(upToken.getPassword()))))
			{
				if(user.unsuccessfullAttemptsCount + 1 >= User.MAX_UNSUCCESSFULL_ATTEMPS)
				{
					database.save(new User(user._id, user.email, user.password, user.roles, user.unsuccessfullAttemptsCount + 1, true, DateTime.now(), user.isDeleted));
				}
				else
				{
					database.save(new User(user._id, user.email, user.password, user.roles, user.unsuccessfullAttemptsCount + 1, user.isLocked, user.lockedTime, user.isDeleted));
				}
				
				throw new LockedAccountException("Your account is locked. Please contact your administrator.");
			}
			else
			{
				// Login will be successful
				if(user.unsuccessfullAttemptsCount != 0)
				{
					database.save(new User(user._id, user.email, user.password, user.roles, 0, user.isLocked, user.lockedTime, user.isDeleted));
				}
			}

			info = new SimpleAuthenticationInfo(username, user.password.toCharArray(), getName());
			info.setCredentialsSalt(ByteSource.Util.bytes(User.SALT));
		}
		catch (Exception e)
		{
			final String message = "There was an error while authenticating user [" + username + "]";
			throw new AuthenticationException(message, e);
		}
		finally
		{
			database.close();
		}

		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) 
	{
		Database database = new Database(DatabaseType.PRODUCTION);		
		User user = database.findOne(User.class, "{email: #}", principals.getPrimaryPrincipal().toString());
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(user.roles);
		
		authorizationInfo.addStringPermissions(ApplicationSpecific.getPermissions(user.roles));
		database.close();
		return authorizationInfo;
	}
}
