'use strict';

const express    = require('express');
const mysql      = require('mysql');
const formidable = require('formidable');
const fs         = require('fs');
const path       = require('path');
const markdown   = require('markdown').markdown;


var app = express();
var connection = mysql.createConnection({
    // socketPath: '//./pipe/MySQL',
    host: 'localhost',
    query: {
        pool: true
    },
    user: 'goodsman',
    password: '9911',
    database: 'goods_manager'
});
connection.connect();


app.get('/', (req, res) => {
    const apiDocumentPath = path.join(__dirname, 'README.md');
    let rs = fs.createReadStream(apiDocumentPath);
    let data = '';
    rs.on('readable', () => {
        let chunk;
        while ((chunk = rs.read()) !== null) {
            data += chunk;
        }
    });
    rs.on('end', () => {
        res.send(markdown.toHTML(data, 'Maruku'));
    });
});


app.get('/list', (req, res) => {
    let sql = 'SELECT * FROM goods';
    if (Object.keys(req.query).length) {
        sql += ' WHERE ';
        let keys = [];
        for (let i of Object.keys(req.query)) {
            switch (i) {
                case 'number':
                case 'broken': {
                    keys.push(i + ' = ' + req.query[i]);
                    break;
                }
                default: {
                    let spl = req.query[i].split('|');
                    if (spl.length > 1) {
                        keys.push('(' + i + ' LIKE("%' + spl.join('%") OR ' + i + ' LIKE("%') + '%"))');
                    }
                    else {
                        keys.push(i + ' LIKE("%' + req.query[i] + '%")');
                    }
                    break;
                }
            }
        }
        sql += keys.join(' AND ');
    }
    console.log('LIST', sql);
    connection.query(sql, null, (err, rows, fields) => {
        if (err) {
            res.sendStatus(500);
            console.log(err);
            return;
        }
        console.log('ITEM_COUNT', rows.length);
        res.send(rows);
    });
});


app.get('/item/:number', (req, res) => {
    let number = req.params.number;
    const sql = 'SELECT * FROM goods WHERE number = ?';
    console.log('ITEM', number);
    connection.query(sql, [number], (err, rows, fields) => {
        if (err) {
            res.sendStatus(500);
            console.log(err);
            return;
        }
        res.send(rows[0]);
    });
});

app.delete('/item/:number', (req, res) => {
    let number = req.params.number;
    const sql = 'DELETE FROM goods WHERE number = ?';
    console.log('DELETE ITEM', number);
    connection.query(sql, [number], (err, rows, fields) => {
        if (err) {
            res.sendStatus(500);
            console.log(err);
            return;
        }
        res.sendStatus(200);
    });
});

app.put('/item/:number', (req, res) => {
    function isFormData(req) {
        let type = req.headers['content-type'] || '';
        return 0 == type.indexOf('multipart/form-data');
    }

    let form = new formidable.IncomingForm();
    let body = {};

    if (!isFormData(req)) {
        res.status(400).end('Bad Request : expecting multipart/form-data');
        return;
    }

    form.on('field', (name, value) => {
        body[name] = value;
    });

    form.uploadDir = savePath;
    form.hash = 'sha1';
    form.on('file', (name, file) => {
        body.image = file.size + '_' + file.hash;
        let newPath = path.join(savePath, body.image);
        fs.rename(file.path, newPath, () => {
            console.log('file ', file.name, ' is uploaded to ', body.image);
        });
    });

    form.on('end', (fields, files) => {
        var sql = 'UPDATE goods SET name = ?, division = ?, broken = ?, codename = ?, content = ?, takeType = ?, takePeriod = ?, image = ? '+
                  'WHERE number = ?';
        console.log('MODIFY', body);
        connection.query(sql,
                        [
                            body.name || '',
                            body.division || '',
                            body.broken || '',
                            body.codename || '',
                            body.content || '',
                            body.takeType || '',
                            body.takePeriod || '',
                            body.image || '',
                            req.params.number
                        ],
                        (err, results, fields) => {
                            if (err) {
                                res.sendStatus(500);
                                console.log(err);
                                return;
                            }
                            res.sendStatus(200);
                        });
    });

    form.parse(req);
});


const savePath = path.join(__dirname, '/image/');

app.post('/upload', (req, res) => {
    function isFormData(req) {
        let type = req.headers['content-type'] || '';
        return 0 == type.indexOf('multipart/form-data');
    }

    let form = new formidable.IncomingForm();
    let body = {};

    if (!isFormData(req)) {
        res.status(400).end('Bad Request : expecting multipart/form-data');
        return;
    }

    form.on('field', (name, value) => {
        body[name] = value;
    });

    form.uploadDir = savePath;
    form.hash = 'sha1';
    form.on('file', (name, file) => {
        body.image = file.size + '_' + file.hash;
        let newPath = path.join(savePath, body.image);
        fs.rename(file.path, newPath, () => {
            console.log('file ', file.name, ' is uploaded to ', body.image);
        });
    });

    form.on('end', (fields, files) => {
        const sql = 'INSERT INTO goods '+
                  '(name, division, broken, codename, content, takeType, takePeriod, image) '+
                  'VALUES (?, ?, ?, ?, ?, ?, ?, ?)';
        console.log('UPLOAD', body);
        connection.query(sql,
                        [
                            body.name || '',
                            body.division || '',
                            body.broken || '',
                            body.codename || '',
                            body.content || '',
                            body.takeType || '',
                            body.takePeriod || '',
                            body.image || ''
                        ],
                        (err, results, fields) => {
                            if (err) {
                                res.sendStatus(500);
                                console.log(err);
                                return;
                            }
                            res.sendStatus(200);
                        });
    });

    form.parse(req);
});

app.use('/image', express.static(savePath, {
    index: false
}));

app.listen(5844);
