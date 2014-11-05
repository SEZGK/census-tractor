var defaultState = "md";
var currentState;

var stateCoordinates = [];
stateCoordinates["md"] = new google.maps.LatLng(39.185433,-77.004032);
stateCoordinates["de"] = new google.maps.LatLng(38.942321,-75.47925);
stateCoordinates["wv"] = new google.maps.LatLng(38.642618,-80.487556);

var stateDistricts = [];
stateDistricts["md"] = 8;
stateDistricts["de"] = 1;
stateDistricts["wv"] = 3;

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
    zoom: 8,
    center: stateCoordinates[currentState],
    mapTypeId: google.maps.MapTypeId.TERRAIN
  };
  
  var tractPolygon;
  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
  
  $.getJSON("http://localhost:8080/data/" + currentState + "/" + numDistricts, function(result) {
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