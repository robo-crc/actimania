package com.framework.helpers;

import java.net.UnknownHostException;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.JacksonMapper;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

public class Database
{
	private Jongo jongo;

	public enum DatabaseType
	{
		TEST_DB,
		TEST_SERVERSERVLET,
		PRODUCTION,
	}

	private MongoClient db;

	public final String databaseName;
	public final DatabaseType databaseType;

	public Database(DatabaseType _databaseType)
	{
		databaseType = _databaseType;
		switch (_databaseType)
		{
		case TEST_DB:
			databaseName = "AppBuilderTestDB" + String.valueOf(Math.round((Math.random() * 10000)));
			break;
		case PRODUCTION:
			databaseName = "AppBuilderProduction";
			break;
		default:
			databaseName = "WrongDatabaseName";
			break;
		}

		// TODO : Detect where we are and assign database string according to location.
		try
		{
			db = new MongoClient("127.0.0.1");
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}

		if(db != null)
		{
			db.setWriteConcern(WriteConcern.SAFE);

			jongo = new Jongo(db.getDB(databaseName), new JacksonMapper.Builder().registerModule(new JodaModule()).enable(MapperFeature.AUTO_DETECT_GETTERS).build());
		}
	}

	public void dropDatabase()
	{
		// Only drop database for test environment.
		if(databaseType == DatabaseType.TEST_DB || databaseType == DatabaseType.TEST_SERVERSERVLET)
		{
			db.dropDatabase(databaseName);
		}
	}
	
	public void close()
	{
		db.close();
	}

	// To be called when we first create the db.
	public void initializeDatabase()
	{
	}

	public <T> void insert(T _toInsert)
	{
		Validate.notNull(_toInsert);

		MongoCollection collection = jongo.getCollection(getCollectionName(_toInsert.getClass()));
		collection.insert(_toInsert);
	}
	
	public <T> void save(T _toSave)
	{
		Validate.notNull(_toSave);

		MongoCollection collection = jongo.getCollection(getCollectionName(_toSave.getClass()));
		collection.save(_toSave);
	}

	public <T> T findOne(Class<T> entityType, ObjectId _id)
	{
		Validate.notNull(_id);
		Validate.notNull(entityType);

		MongoCollection collection = jongo.getCollection(getCollectionName(entityType));

		return collection.findOne(_id).as(entityType);
	}

	public <T> T findOne(Class<T> entityType, String query)
	{
		Validate.notEmpty(query);
		Validate.notNull(entityType);

		MongoCollection collection = jongo.getCollection(getCollectionName(entityType));

		return collection.findOne(query).as(entityType);
	}
	
	public <T> T findOne(Class<T> entityType, String query, Object ... parameters)
	{
		Validate.notEmpty(query);
		Validate.notNull(entityType);

		MongoCollection collection = jongo.getCollection(getCollectionName(entityType));

		return collection.findOne(query, parameters).as(entityType);
	}

	public <T> Iterable<T> find(Class<T> entityType, String query)
	{
		Validate.notEmpty(query);
		Validate.notNull(entityType);

		MongoCollection collection = jongo.getCollection(getCollectionName(entityType));

		return collection.find(query).as(entityType);
	}
	
	public <T> Iterable<T> find(Class<T> entityType, String query, Object ... parameters)
	{
		Validate.notEmpty(query);
		Validate.notNull(entityType);

		MongoCollection collection = jongo.getCollection(getCollectionName(entityType));

		return collection.find(query, parameters).as(entityType);
	}

	private <T> String getCollectionName(Class<T> entityType)
	{
		return entityType.getSimpleName().toLowerCase().concat("s");
	}
}
