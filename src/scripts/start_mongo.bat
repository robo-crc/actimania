if not exist ..\..\tmp\mongodb\data mkdir ..\..\tmp\mongodb\data
if not exist ..\..\tmp\mongodb\logs mkdir ..\..\tmp\mongodb\logs
START /B "" "../../tools/mongodb/mongod.exe" --logpath ../../tmp/mongodb/logs/log.txt --logappend --dbpath ../../tmp/mongodb/data