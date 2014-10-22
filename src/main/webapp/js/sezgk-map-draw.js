function initialize() {
  var mapOptions = {
    zoom: 10,
    center: new google.maps.LatLng(39.624405, -78.778477),
    mapTypeId: google.maps.MapTypeId.TERRAIN
  };
  
  var tractPolygon;
  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
  
  $.getJSON("http://localhost:8080/data/md", function(result) {
    var usedColors = [];
    
    for (var n = 0; n < result.length; n++) {
      var tracts = result[n].censusTracts;
      var color = Colors.random();

      while ($.inArray(color, usedColors) !== -1) {
        color = Colors.random();
      }
      
      usedColors.push(color);

      for (var i = 0; i < tracts.length; i++) {
        var boundaries = tracts[i].boundaries;

        for (var j = 0; j < boundaries.length; j++) {
          var points = [];
          var boundary = boundaries[j];
        
          for (var m = 0; m < boundary.coordinates.length; m++) {
            var coordinate = boundary.coordinates[m];
            points.push(new google.maps.LatLng(coordinate.latitude, coordinate.longitude));
          }
        
          tractPolygon = new google.maps.Polygon({
            paths: points,
            strokeColor: '#FF0000',
            strokeOpacity: 0.0,
            strokeWeight: 2,
            fillColor: color.rgb,
            fillOpacity: 0.5
          });

          tractPolygon.setMap(map);
        }
      }
    }
  });
}

google.maps.event.addDomListener(window, 'load', initialize);