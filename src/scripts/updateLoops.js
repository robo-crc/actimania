use LoopsProduction
db.dropDatabase()
sleep(300);
db.copyDatabase("LoopsProduction","LoopsProduction","localhost:27018")
sleep(1000);