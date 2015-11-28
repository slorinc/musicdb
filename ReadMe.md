# musicdb

RestAPI that by querying three different public APIs (MusicBrainz, Wikipedia, CoverArt) compiles a result containing
the following information for the submitted Artist MbId (MusicBrains ID):
 * mbid of the artist
 * description field contains the extract of the artist's wikipedia page
 * list of albums complete with their mbid and cover art image url
 
Heroku
--

Deployed to:
    https://musicdb.herokuapp.com/ 

Endpoint(s)
--

Query

    curl -X GET https://musicdb.herokuapp.com/musicdb/public/mbid/{mdid}
        

Metrics

    curl -X GET https://musicdb.herokuapp.com/musicdb/metrics

TODO
--

 * further optimize the response time by storing the results in Redis with record expire date of one day
 * further optimize the response time by keeping track of the most popular queries and automatically refreshing
   the results every day into Redis
