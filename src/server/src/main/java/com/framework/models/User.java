package com.framework.models;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Database;
import com.framework.helpers.Helpers;
import com.framework.helpers.LocalizedString;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class User 
{
	@JsonIgnore
	public static final int			MAX_UNSUCCESSFULL_ATTEMPS = 20;
	@JsonIgnore
	public static final Object 		SALT = "UH97184!*(&/ iugf ^'.";
	@JsonIgnore
	public static final int 		ENCRYPT_ITERATIONS = 358;
	@JsonIgnore
	public static final int 		MINIMUM_PASSWORD_LENGTH = 6;
	
	
	public final ObjectId 			_id;
	public final String				email;
	public final String				password;
	public final Set<String>		roles;
	public final int 				unsuccessfullAttemptsCount;
	public final boolean			isLocked;
	public final DateTime 			lockedTime;
	public final boolean			isDeleted;
	
	public User(@JsonProperty("_id") 						ObjectId 			_loginId,
				@JsonProperty("email")						String 				_email,
				@JsonProperty("password")					String				_password,
				@JsonProperty("role")						Set<String>			_roles,
				@JsonProperty("unsuccessfullAttemptsCount")	int 				_unsuccessfullAttemptsCount,
				@JsonProperty("isLocked")					boolean 			_isLocked,
				@JsonProperty("lockedTime")					DateTime 			_lockedTime,
				@JsonProperty("isdeleted")					boolean				_isDeleted
			)
	{
		_id 						= _loginId;
		email 						= _email;
		password 					= _password;
		roles 						= _roles;
		unsuccessfullAttemptsCount 	= _unsuccessfullAttemptsCount;
		isLocked 					= _isLocked;
		lockedTime					= _lockedTime;
		isDeleted					= _isDeleted;
	}
	
	public static User changeEmail(Database database, User login, String newEmail)
	{
		User newLogin = new User(login._id, newEmail, login.password, login.roles, login.unsuccessfullAttemptsCount, login.isLocked, login.lockedTime, login.isDeleted);
		database.save(newLogin);
		return newLogin;
	}
	
	public static User changePassword(Database database, User login, String newPassword)
	{
		User newLogin = new User(login._id, login.email, encryptPassword(newPassword), login.roles, login.unsuccessfullAttemptsCount, login.isLocked, login.lockedTime, login.isDeleted);
		database.save(newLogin);
		return newLogin;
	}
	
	public static void resetPassword(Database database, String email) throws AuthorizationException
	{
		SecurityUtils.getSubject().checkPermission("users");
		User login = database.findOne(User.class, "{email : #}", email);
		
		if(login == null)
		{
			return;
		}
		
		String newPassword = Helpers.randomString(8);
		// Send e-mail with new password

		User newLogin = new User(login._id, login.email, encryptPassword(newPassword), login.roles, login.unsuccessfullAttemptsCount, login.isLocked, login.lockedTime, login.isDeleted);
		database.save(newLogin);
	}
	
	// Return secured hash.
	public static String encryptPassword(String password)
	{
		return (new Sha512Hash(password, SALT, ENCRYPT_ITERATIONS)).toBase64();
	}
	
	public static boolean validatePassword(Essentials essentials, String password)
	{
		if(password.length() < MINIMUM_PASSWORD_LENGTH)
		{
			LocalizedString strNotLongEnoughPassword = new LocalizedString(ImmutableMap.of( 	
					Locale.ENGLISH, "Password is " + password.length() + " characters long and must be " + MINIMUM_PASSWORD_LENGTH + " characters long.", 
					Locale.FRENCH, 	"Le mot de passe reçu est " + password.length() + " caractères de long, mais devrait contenir un minimum de " + MINIMUM_PASSWORD_LENGTH + " caractères"
					), Helpers.getCurrentLocale());
			
			essentials.errorList.add(strNotLongEnoughPassword);
			
			return false;
		}
		
		return true;
	}
	
	public static boolean validateEmail(Essentials essentials, String password)
	{
	/*	LocalizedString strInvalidEmail = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Email is invalid.", 
				Locale.FRENCH, 	"Le courriel est invalide."
				), Helpers.getCurrentLocale());
		*/
		// TODO : Take a regex on the net
		
		return true;
	}
	
	public static User createUser(Essentials essentials, Set<String> roles, String email, String password)
	{
		Helpers.checkWritePermission(essentials, User.class);
		
		User user = essentials.database.findOne(User.class, "{email : #}", email);
		
		if(user != null)
		{
			LocalizedString strUserAlreadyExist = new LocalizedString(ImmutableMap.of( 	
					Locale.ENGLISH, "Email is invalid.", 
					Locale.FRENCH, 	"Le courriel est invalide."
					), Helpers.getCurrentLocale());
			
			essentials.errorList.add(strUserAlreadyExist);
			
			return null;
		}
		
		validateEmail(essentials, email);
		// In case of invalid password, it throws.
		validatePassword(essentials, password);

		user = new User(null, email, encryptPassword(password), roles, 0, false, null, false);
		essentials.database.save(user);
		
		return user;
	}
	
	public static User editUser(Essentials essentials, ObjectId id, Set<String> roles, String email, String password) 
	{
		Helpers.checkWritePermission(essentials, User.class);
		
		User user = essentials.database.findOne(User.class, id);
		if(user != null)
		{
			String passwordToUse = user.password;
			if(!password.isEmpty())
			{
				// Keep old password
				if(validatePassword(essentials, password))
				{
					passwordToUse = encryptPassword(password);
				}
				else
				{
					// Invalid password passed
					return null;
				}
			}
			user = new User(id, email, passwordToUse, roles, user.unsuccessfullAttemptsCount, user.isLocked, user.lockedTime, user.isDeleted);
			essentials.database.save(user);
			
			return user;
		}
		else
		{
			// Already exist
			return null;
		}
	}
	
	public static void deleteUser(Essentials essentials, ObjectId id)
	{
		Helpers.checkWritePermission(essentials, User.class);
		
		User user = essentials.database.findOne(User.class, id);
		
		if(user != null)
		{
			User userDeleted = new User(user._id, user.email, user.password, user.roles, user.unsuccessfullAttemptsCount, user.isLocked, user.lockedTime, true);
			essentials.database.save(userDeleted);
		}
	}
	
	public static User getUser(Essentials essentials, String email)
	{
		return essentials.database.findOne(User.class, "{ email : # }", email);
	}
	
	public static ArrayList<User> getAllUsers(Essentials essentials)
	{
		Helpers.checkReadPermission(essentials, User.class);
		
		ArrayList<User> allLogins = Lists.newArrayList(essentials.database.find(User.class, "{ isDeleted : false }"));

		return allLogins;
	}
}
