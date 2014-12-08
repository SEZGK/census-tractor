$(document).ready(function() {
  initialize();
});

function initialize() {
  var currentState = "md";
  var numDistricts = 8;

  var mapOptions = {
    zoom: 7,
    center: new google.maps.LatLng(39.0, -73.610112),
    mapTypeId: google.maps.MapTypeId.TERRAIN
  };
  
  var tractPolygon;
  var map = new google.maps.Map(document.getElementById('political-canvas'), mapOptions);
  
  $.getJSON("http://localhost:8080/data/" + currentState + "/" + numDistricts, function(result) {
    var usedColors = [];
    
    for (var n = 0; n < result.length; n++) {
      var center = new google.maps.LatLng(result[n].center.latitude, result[n].center.longitude);
      var tracts = result[n].censusTracts;
      var color;
      var opacity;
      
      var republicans = result[n].republicans;
      var democrats = result[n].democrats;
      var independents = result[n].independents;
      var total = republicans + democrats + independents;
      
      if ((democrats / total) > 0.6) {
      	color = '#0000FF';
      	opacity = democrats / total;
      } else if ((republicans / total) > 0.6) {
      	color = '#FFFF00';
      	opacity = republicans / total;
      } else {
      	color = '#CC33FF';
      	opacity = 0.5;
      }
      
      var infowindow = new google.maps.InfoWindow({
        content: '<div style="width:150px">' + 'District: ' + (n + 1) + '<br>' + 'Population: ' + result[n].districtPopulation + '<br>' 
        				+ 'Republicans: ' + republicans + '<br>' + 'Democrats: ' + democrats + '<br>' + 'Independents: ' + independents + '</div>'
      });
    
      var marker = new google.maps.Marker({
        position: center,
        map: map,
        info: infowindow
      });
      
      google.maps.event.addListener(marker, 'mouseover', function() {
        this.info.open(map, this);
      });
      
      google.maps.event.addListener(marker, 'mouseout', function() {
        this.info.close();
      });

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
            map: map,
            strokeColor: '#FF0000',
            strokeOpacity: 0.0,
            strokeWeight: 2,
            fillColor: color,
            fillOpacity: opacity
          });
        }
      }
    }
  });
}