var defaultState = "md";
var currentState;

var stateCoordinates = [];
stateCoordinates["al"] = new google.maps.LatLng(32.739734, -86.548901);
stateCoordinates["ak"] = new google.maps.LatLng(65.649730, -151.567989);
stateCoordinates["az"] = new google.maps.LatLng(34.540084, -111.799071);
stateCoordinates["ar"] = new google.maps.LatLng(34.751928, -92.329132);
stateCoordinates["ca"] = new google.maps.LatLng(37.463966, -120.347075);
stateCoordinates["co"] = new google.maps.LatLng(39.066207, -105.561553);
stateCoordinates["ct"] = new google.maps.LatLng(41.675980, -72.669616);
stateCoordinates["de"] = new google.maps.LatLng(38.942321, -75.47925);
stateDistricts["dc"] = new google.maps.LatLng();
stateDistricts["fl"] = new google.maps.LatLng(28.764636, -81.747019);
stateDistricts["ga"] = new google.maps.LatLng(33.069728, -82.395823);
stateDistricts["hi"] = new google.maps.LatLng(20.824374, -156.589755);
stateDistricts["id"] = new google.maps.LatLng(44.474184, -114.387814);
stateDistricts["il"] = new google.maps.LatLng(39.902591, -89.338986);
stateDistricts["in"] = new google.maps.LatLng(39.846776, -86.164377);
stateDistricts["ia"] = new google.maps.LatLng(42.281635, -93.679025);
stateDistricts["ks"] = new google.maps.LatLng(38.630022, -98.436105);
stateDistricts["ky"] = new google.maps.LatLng(37.687013, -795628);
stateDistricts["la"] = new google.maps.LatLng(31.281999, -92.156468);
stateDistricts["me"] = new google.maps.LatLng(45.200365, -69.425755);
stateCoordinates["md"] = new google.maps.LatLng(39.185433,-77.004032);
stateDistricts["ma"] = new google.maps.LatLng(42.377759, -72.205296);
stateDistricts["mi"] = new google.maps.LatLng(38.638482, -92.681458);
stateDistricts["mn"] = new google.maps.LatLng(46.031433);
stateDistricts["ms"] = new google.maps.LatLng();
stateDistricts["mo"] = new google.maps.LatLng();
stateDistricts["mt"] = new google.maps.LatLng();
stateDistricts["ne"] = new google.maps.LatLng();
stateDistricts["nv"] = new google.maps.LatLng();
stateDistricts["nh"] = new google.maps.LatLng();
stateDistricts["nj"] = new google.maps.LatLng();
stateDistricts["nm"] = new google.maps.LatLng();
stateDistricts["ny"] = new google.maps.LatLng();
stateDistricts["nc"] = new google.maps.LatLng();
stateDistricts["nd"] = new google.maps.LatLng();
stateDistricts["oh"] = new google.maps.LatLng();
stateDistricts["ok"] = new google.maps.LatLng();
stateDistricts["or"] = new google.maps.LatLng();
stateDistricts["pa"] = new google.maps.LatLng();
stateDistricts["ri"] = new google.maps.LatLng();
stateDistricts["sc"] = new google.maps.LatLng();
stateDistricts["sd"] = new google.maps.LatLng();
stateDistricts["tn"] = new google.maps.LatLng();
stateDistricts["tx"] = new google.maps.LatLng();
stateDistricts["ut"] = new google.maps.LatLng();
stateDistricts["vt"] = new google.maps.LatLng();
stateDistricts["va"] = new google.maps.LatLng();
stateDistricts["wa"] = new google.maps.LatLng();
stateDistricts["wv"] = new google.maps.LatLng();
stateCoordinates["wv"] = new google.maps.LatLng(38.642618,-80.487556);
stateDistricts["wi"] = new google.maps.LatLng();
stateCoordinates["wy"] = new google.maps.LatLng(43.096027, -107.652833);

var stateDistricts = [];
stateDistricts["al"] = 7;
stateDistricts["ak"] = 1;
stateDistricts["az"] = 9;
stateDistricts["ar"] = 4;
stateDistricts["ca"] = 53;
stateDistricts["co"] = 7;
stateDistricts["ct"] = 5;
stateDistricts["de"] = 1
stateDistricts["dc"] = 
stateDistricts["fl"] = 27
stateDistricts["ga"] = 14
stateDistricts["hi"] = 2
stateDistricts["id"] = 2
stateDistricts["il"] = 18
stateDistricts["in"] = 9
stateDistricts["ia"] = 4
stateDistricts["ks"] = 4
stateDistricts["ky"] = 6
stateDistricts["la"] = 6
stateDistricts["me"] = 2
stateDistricts["md"] = 8
stateDistricts["ma"] = 9
stateDistricts["mi"] = 14
stateDistricts["mn"] = 8
stateDistricts["ms"] = 4
stateDistricts["mo"] = 8
stateDistricts["mt"] = 1
stateDistricts["ne"] = 3
stateDistricts["nv"] = 4
stateDistricts["nh"] = 2
stateDistricts["nj"] = 12
stateDistricts["nm"] = 3
stateDistricts["ny"] = 27
stateDistricts["nc"] = 13
stateDistricts["nd"] = 1
stateDistricts["oh"] = 16
stateDistricts["ok"] = 5
stateDistricts["or"] = 5
stateDistricts["pa"] = 18
stateDistricts["ri"] = 2
stateDistricts["sc"] = 7
stateDistricts["sd"] = 1
stateDistricts["tn"] = 9
stateDistricts["tx"] = 36
stateDistricts["ut"] = 4
stateDistricts["vt"] = 1
stateDistricts["va"] = 11
stateDistricts["wa"] = 10
stateDistricts["wv"] = 3
stateDistricts["wi"] = 8
stateDistricts["wy"] = 1

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
      var color = randomColor(n);

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
            strokeColor: '#FF0000',
            strokeOpacity: 0.0,
            strokeWeight: 2,
            fillColor: color,
            fillOpacity: 0.5
          });

          tractPolygon.setMap(map);
        }
      }
    }
  });
}