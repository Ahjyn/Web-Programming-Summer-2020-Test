/**
 * Created by Vijaya Yeruva on 5/27/2017.
 */
var MongoClient = require('mongodb').MongoClient;
var url = 'mongodb://lesson7:lesson7@ds159563.mlab.com:59563/aplwebdemo';

MongoClient.connect(url, {useNewUrlParser: true}, function (err, db) {
    if (err) throw err;
    var dbase = db.db("aplwebdemo");
    dbase.createCollection("newCollection", function (err, res) {
        if (err) throw err;
        console.log("Collection created!");
        db.close();
    });
});
