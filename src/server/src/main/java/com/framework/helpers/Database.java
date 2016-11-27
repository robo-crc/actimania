package com.framework.helpers;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.Mapper;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.JacksonMapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

// Database class should be thread safe.
public class Database
{
	// Jongo client is thread safe
	private Jongo jongo;

	public enum DatabaseType
	{
		TEST_DB,
		TEST_SERVERSERVLET,
		PRODUCTION,
	}

	// Mongo client is thread safe.
	private MongoClient db;

	public final String databaseName;
	public final DatabaseType databaseType;

	public Database(DatabaseType _databaseType)
	{
		databaseType = _databaseType;
		switch (_databaseType)
		{
		case TEST_DB:
			databaseName = ApplicationSpecific.getDatabaseName() + "TestDB" + String.valueOf(Math.round((Math.random() * 10000)));
			break;
		case PRODUCTION:
			databaseName = ApplicationSpecific.getDatabaseName() + "Production";
			break;
		default:
			databaseName = "WrongDatabaseName";
			break;
		}

		// TODO : Detect where we are and assign database string according to location.
		db = new MongoClient("127.0.0.1");

		if(db != null)
		{
			db.setWriteConcern(WriteConcern.SAFE);

			Mapper objectMapper = new JacksonMapper.Builder()
			.registerModule(new JodaModule()).enable(MapperFeature.AUTO_DETECT_GETTERS)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.build();
			jongo = new Jongo(db.getDB(databaseName), objectMapper);		
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
	
	public <T> WriteResult remove(Class<T> entityType, ObjectId _id)
	{
		Validate.notNull(entityType);
		Validate.notNull(_id);
		
		MongoCollection collection = jongo.getCollection(getCollectionName(entityType));
		
		return collection.remove(_id);
	}
	
	public <T> void dropCollection(Class<T> entityType)
	{
		Validate.notNull(entityType);
		
		MongoCollection collection = jongo.getCollection(getCollectionName(entityType));
		
		collection.drop();
	}


	private <T> String getCollectionName(Class<T> entityType)
	{
		return entityType.getSimpleName().toLowerCase().concat("s");
	}
}
