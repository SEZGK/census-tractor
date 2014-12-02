$(document).ready(function() {
	google.maps.event.addDomListener(window, 'load', initialize(defaultState, stateDistricts[defaultState]));
	
    var stateMenu = document.getElementById('stateMenu');
    var districtsPicker = document.getElementById('districtsPicker');
    
    stateMenu.value = defaultState;
    districtsPicker.value = stateDistricts[defaultState];
    currentState = defaultState;
    
    stateMenu.addEventListener("change", function() {
        currentState = stateMenu[stateMenu.selectedIndex].value;
        districtsPicker.value = stateDistricts[currentState];
        initialize(currentState, stateDistricts[currentState]);
    });
    
    districtsPicker.addEventListener("change", function() {
		initialize(currentState, districtsPicker.value);
    });
});

function initialize(currentState, numDistricts) {
  var mapOptions = {
    zoom: zoomLevel[currentState],
    center: stateCoordinates[currentState],
    mapTypeId: google.maps.MapTypeId.TERRAIN
  };
  
  var tractPolygon;
  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
  
  $.getJSON("http://localhost:8080/data/" + currentState + "/" + numDistricts, function(result) {
    var usedColors = [];
    
    for (var n = 0; n < result.length; n++) {
      var center = new google.maps.LatLng(result[n].center.latitude, result[n].center.longitude);
      var tracts = result[n].censusTracts;
      var color = randomColor(n);
      
      var infowindow = new google.maps.InfoWindow({
        content: '<div style="width:150px">' + 'District: ' + (n + 1) + '<br>' + 'Population: ' + result[n].districtPopulation + '</div>',
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
      
	/*
      while ($.inArray(color, usedColors) !== -1) {
        color = randomColor(n);
      }
      */
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
            map: map,
            strokeColor: '#FF0000',
            strokeOpacity: 0.0,
            strokeWeight: 2,
            fillColor: color,
            fillOpacity: 0.5
          });
        }
      }
    }
  });
}