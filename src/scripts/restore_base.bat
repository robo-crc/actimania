if not exist ..\..\tmp\mongodb\data mkdir ..\..\tmp\mongodb\data
:: Change --db GameProduction to the correct value
..\..\tools\mongodb\mongorestore.exe ..\..\data\Base --db GameProduction