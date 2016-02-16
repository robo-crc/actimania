use PythagoriumProduction
db.dropDatabase()
sleep(100);
db.copyDatabase("PythagoriumProduction","PythagoriumProduction","localhost:27018")
sleep(1000);