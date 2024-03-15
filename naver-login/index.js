const express = require('express');

const app = express();
const port = 8001;


app.use(express.static('public'));

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});

app.get('/login/naver', (req, res) => {
    const API_URL = 'https://nid.naver.com/oauth2.0/authorize';
    client_id + '&redirect_uri=' + redirectURI + '&state=' + state;

    res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'});
    res.end("<a href='"+ api_url + "'><img height='50' src='http://static.nid.naver.com/oauth/small_g_in.PNG'/></a>");
});

app.get('/oauth/naver', (req, res) => {
    console.log(req.query);
    console.log(req.body);
});

app.listen(port, () => {
    console.log(`Server running on port #${port}.`);
});