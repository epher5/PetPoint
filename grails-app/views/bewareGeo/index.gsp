<!DOCTYPE html>
<html>
<head>
    <title>PetPoint - Locations of Aggressive Pets</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
    /* Always set the map height explicitly to define the size of the div
     * element that contains the map. */
    #map {
        height: 95%;
    }
    /* Optional: Makes the sample page fill the window. */
    html, body {
        height: 100%;
        margin: 0;
        padding: 0;
    }
    #floating-panel {
        position: absolute;
        top: 52px;
        left: 8%;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
        text-align: center;
        font-family: 'Roboto','sans-serif';
        line-height: 30px;
        padding-left: 10px;
    }
    a {
        text-decoration:none;
    }
    </style>
</head>
<body>
<div style="padding: 10px; font-family: sans-serif; border-style: groove; font-size: 20px; border-color: cornsilk; border-width: 1px; background-color: rgba(222, 184, 135, 0.95); width: 100%;">
    <div style="float:right;margin-right: 25px;color: antiquewhite;"><g:link class="list" controller="home" action="index">Home</g:link></div>
    <div style="padding-left: 8px;display: inline-block;color: antiquewhite;"><g:link class="list" controller="abuserGeo" action="index">Locations of Animal Abusers</g:link></div>
    <div style="padding-left: 20px;display: inline-block;color: antiquewhite;"><g:link class="list" controller="bewareGeo" action="index">>Locations of Aggressive Pets<</g:link></div>
</div>
<div id="floating-panel">
    <input id="address" type="textbox" value="">
    <input id="submit" type="button" value="Location">
</div>
<div id="map"></div>
<script>
    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
            zoom: 10,
            center: new google.maps.LatLng(38.6270, -90.1994),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        });
        infowindow = new google.maps.InfoWindow();
        geocoder = new google.maps.Geocoder();
        var i;
        for (i = 0; i < locations.length; i++) {
            geocodeAddress(locations[i]);
        }
        document.getElementById('submit').addEventListener('click', function() {
            geocodeAddress(geocoder, map);
        });
    }

    function geocodeAddress(geocoder, resultsMap) {
        var address = document.getElementById('address').value;
        geocoder.geocode({'address': address}, function(results, status) {
            if (status === 'OK') {
                resultsMap.setCenter(results[0].geometry.location);
                var marker = new google.maps.Marker({
                    map: resultsMap,
                    position: results[0].geometry.location
                });
            } else {
                alert('Geocode was not successful for the following reason: ' + status);
            }
        });
    }
    var locations = [];
    <g:each in="${aggressives}" var="aggresive" status="counter">
    locations.push(['${aggresive.breed}', '${aggresive.getFullAddress()}, USA']);
    </g:each>

    var map;
    var infowindow;
    var geocoder;

    function geocodeAddress(location) {
        geocoder.geocode( { 'address': location[1]}, function(results, status) {
            console.log(status);
            if (status == google.maps.GeocoderStatus.OK) {

                console.log(results[0].geometry.location);
                map.setCenter(results[0].geometry.location);
                createMarker(results[0].geometry.location,location[0]+"<br>"+location[1]);
            }
            else
            {
                console.log("some problem in geocode" + status);
            }
        });
    }

    function createMarker(latlng,html){
        var marker = new google.maps.Marker({
            position: latlng,
            map: map
        });

        google.maps.event.addListener(marker, 'mouseover', function() {
            infowindow.setContent(html);
            infowindow.open(map, marker);
        });

        google.maps.event.addListener(marker, 'mouseout', function() {
            infowindow.close();
        });
    }
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDFoxnbmgGL0N5m0bYAgwiF4ftjSy0LbcE&callback=initMap">
</script>
</body>
</html>