var path = require('path');
var fs = require('fs');
var http = require('http');
var querystring = require('querystring');

var users = {
  'user': {
    pwd: 'user',
    role: 'user'
  }, 'admin': {
    pwd: 'admin',
    role: 'admin'
  }
};

var messages = [{user: 'admin', content: 'welcome to here!'}, {user: 'user', content: '沙发！'}];

function getMimeType(pathname) {
  console.log(pathname);
  var validExtensions = {
    ".html" : "text/html",
    ".js": "application/javascript",
    ".css": "text/css",
    ".jpg": "image/jpeg",
    ".gif": "image/gif",
    ".png": "image/png"
  };
  var ext = path.extname(pathname);
  var type = validExtensions[ext];
  return type;
}

function handlePage(req, res, pathname, replaceList) {
  if (pathname == '/') pathname = '/index.html';
  var filePath = __dirname + pathname;
  var mimeType = getMimeType(pathname);
  if (fs.existsSync(filePath)) {
    fs.readFile(filePath, 'utf8', function(err, rawData){
      if (err) {
        res.writeHead(500);
        res.end();
      } else {
        var data = rawData;
        // res.setHeader("Content-Length", data.length);
        res.setHeader("Content-Type", mimeType);
        res.statusCode = 200;
        if (replaceList) {
          for (item in replaceList) {
            var re = new RegExp('{{#'+item+'}}', 'g');
            data = data.replace(re, replaceList[item]);
          }
        }
        // console.log(data);
        res.write(data);
        res.end();
      }
    });
  } else {
    res.writeHead(500);
    res.end();
  }
}

var server = http.createServer(function(req, res){
  if (req.method == 'GET') {
    // console.log('request:', req.method, '', req.url);

    var rc = req.headers.cookie;
    if (rc) {
      var cookie = {};
      var temp = rc.split(';');
      temp.forEach(function(c) {
        var t = c.replace(/\s/g, '').split('=');
        cookie[t[0]] = t[1];
      });
      if (cookie['username'] && users[cookie['username']]) {
        user = users[cookie['username']];
        var content = '';
        messages.forEach(function(msg) {
          content += '<div>' + msg.user + ': ' + msg.content + '</div>';
        });
        handlePage(req, res, '/success.html', {user: cookie['username'], role: user.role, content: content});
      } else {
        var url = req.url;
        url = url.replace('/?', '');
        handlePage(req, res, req.url);
      }
    } else {
      var url = req.url;
      url = url.replace('/?', '');
      handlePage(req, res, req.url);
    }

  } else if (req.method == 'POST') {

    var trunk = '';
    req.on('data', function(data) {
      trunk += data;
    });


    if (req.url == '/') {
      req.on('end', function(err) {
        var post = querystring.parse(trunk);
        var user = users[post['name']];
        if (user && user.pwd === post['pwd']) {
          res.setHeader('Set-Cookie', 'username=' + post['name']);
          var content = '';
          messages.forEach(function(msg) {
            content += '<div>' + msg.user + ': ' + msg.content + '</div>';
          });
          handlePage(req, res, '/success.html', {user: post['name'], role: user.role, content: content});
        }
      });
    } else {
      req.on('end', function(err) {
        var rc = req.headers.cookie;
        var cookie = {};
        if (rc) {
          var temp = rc.split(';');
          temp.forEach(function(c) {
            var t = c.replace(/\s/g, '').split('=');
            cookie[t[0]] = t[1];
          });
        }
        var post = querystring.parse(trunk);
        var user = users[cookie.username];
        if (!user) {
          handlePage(req, res, '/');
          return;
        }
        messages.push({user: cookie.username, content: post.content});
        var content = '';
        messages.forEach(function(msg) {
          content += '<div>' + msg.user + ': ' + msg.content + '</div>';
        });
        // console.log(user);
        handlePage(req, res, '/success.html', {user: cookie.username, role: user.role, content: content});
      });
    }

  }
});
server.listen(3000);
console.log('listen port 3000'); 
