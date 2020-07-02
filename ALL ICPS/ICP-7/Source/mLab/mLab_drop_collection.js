/**
 * Created by Vijaya Yeruva on 5/27/2017.
 */

var MongoClient = require('mongodb').MongoClient;
var url = 'mongodb://lesson7:lesson7@ds159563.mlab.com:59563/aplwebdemo';

MongoClient.connect(url, {useNewUrlParser: true},function(err, db) {
    if (err) throw err;
    var dbase = db.db("aplwebdemo");
    dbase.dropCollection("newCollection", function(err, delOK) {
        if (err) throw err;
        if (delOK) console.log("Collection deleted");
        db.close();
    });
});
