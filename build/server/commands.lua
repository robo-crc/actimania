function preBuild()
	-- fjoanis : This code is commented because the server and the build system is on the same system.
	-- Make sure mongodb is not running.
	--os.execute("taskkill /IM mongod.exe /F")
		
	local logsPath = path.getabsolute("../tmp/mongodb/logs/log.txt")
	local dataPath = path.getabsolute("../tmp/mongodb/data")

	-- We really need to clean this dir because we don't quit gracefully
	-- os.rmdir(path.getabsolute("../tmp/mongodb"))
	
	os.mkdir(dataPath)
	
	local mongodbPath = path.getabsolute("../tools/mongodb")
	local toExec = "START /B " .. mongodbPath .. "/mongod --logpath " .. logsPath .. " --logappend --dbpath " .. dataPath

	executeCmd(toExec)
end

function postBuild()
	--executeCmd("taskkill /IM mongod.exe /F")
end

function preTests()
end

function postTests()
end