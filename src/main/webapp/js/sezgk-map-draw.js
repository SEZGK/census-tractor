var defaultState = "md";
var currentState;

var stateCoordinates = [];
stateCoordinates["al"] = new google.maps.LatLng(32.739734, -86.548901);
stateCoordinates["ak"] = new google.maps.LatLng(65.649730, -151.567989);
stateCoordinates["az"] = new google.maps.LatLng(34.161818, -111.633797);
stateCoordinates["ar"] = new google.maps.LatLng(34.751928, -92.329132);
stateCoordinates["ca"] = new google.maps.LatLng(37.463966, -120.347075);
stateCoordinates["co"] = new google.maps.LatLng(39.066207, -105.561553);
stateCoordinates["ct"] = new google.maps.LatLng(41.675980, -72.669616);
stateCoordinates["de"] = new google.maps.LatLng(39.134321, -75.564394);
stateCoordinates["dc"] = new google.maps.LatLng(38.899774, -77.037846);
stateCoordinates["fl"] = new google.maps.LatLng(27.926474, -81.827888);
stateCoordinates["ga"] = new google.maps.LatLng(33.069728, -82.395823);
stateCoordinates["hi"] = new google.maps.LatLng(20.824374, -156.589755);
stateCoordinates["id"] = new google.maps.LatLng(44.527843, -114.393082);
stateCoordinates["il"] = new google.maps.LatLng(39.902591, -89.338986);
stateCoordinates["in"] = new google.maps.LatLng(39.846776, -86.164377);
stateCoordinates["ia"] = new google.maps.LatLng(42.281635, -93.679025);
stateCoordinates["ks"] = new google.maps.LatLng(38.630022, -98.436105);
stateCoordinates["ky"] = new google.maps.LatLng(37.796763, -85.343513);
stateCoordinates["la"] = new google.maps.LatLng(31.281999, -92.156468);
stateCoordinates["me"] = new google.maps.LatLng(45.200365, -69.425755);
stateCoordinates["md"] = new google.maps.LatLng(39.185433, -77.004032);
stateCoordinates["ma"] = new google.maps.LatLng(42.377759, -72.205296);
stateCoordinates["mi"] = new google.maps.LatLng(44.402392, -85.608902);
stateCoordinates["mn"] = new google.maps.LatLng(46.031433, -94.505189);
stateCoordinates["ms"] = new google.maps.LatLng(32.958143, -89.715150);
stateCoordinates["mo"] = new google.maps.LatLng(38.638482, -92.681458);
stateCoordinates["mt"] = new google.maps.LatLng(46.845164, -110.326424);
stateCoordinates["ne"] = new google.maps.LatLng(41.653749, -99.866058);
stateCoordinates["nv"] = new google.maps.LatLng(39.597978, -116.972296);
stateCoordinates["nh"] = new google.maps.LatLng(43.92955, -71.467781);
stateCoordinates["nj"] = new google.maps.LatLng(40.100573, -74.530932);
stateCoordinates["nm"] = new google.maps.LatLng(34.438503, -106.083501);
stateCoordinates["ny"] = new google.maps.LatLng(43.026267, -75.811336);
stateCoordinates["nc"] = new google.maps.LatLng(35.693996, -79.339746);
stateCoordinates["nd"] = new google.maps.LatLng(47.596599, -100.504355);
stateCoordinates["oh"] = new google.maps.LatLng(40.064606, -82.879799);
stateCoordinates["ok"] = new google.maps.LatLng(35.723484, -97.293648);
stateCoordinates["or"] = new google.maps.LatLng(44.130809, -120.702394);
stateCoordinates["pa"] = new google.maps.LatLng(40.874885, -77.653061);
stateCoordinates["ri"] = new google.maps.LatLng(41.733543, -71.588333);
stateCoordinates["sc"] = new google.maps.LatLng(34.019851, -80.614898);
stateCoordinates["sd"] = new google.maps.LatLng(44.516121, -100.388178);
stateCoordinates["tn"] = new google.maps.LatLng(35.830024, -85.803438);
stateCoordinates["tx"] = new google.maps.LatLng(31.715963, -99.110915);
stateCoordinates["ut"] = new google.maps.LatLng(39.499711, -111.603357);
stateCoordinates["vt"] = new google.maps.LatLng(43.992072, -72.634071);
stateCoordinates["va"] = new google.maps.LatLng(37.578749, -78.601573);
stateCoordinates["wa"] = new google.maps.LatLng(47.381425, -120.236667);
stateCoordinates["wv"] = new google.maps.LatLng(38.642618,-80.487556);
stateCoordinates["wi"] = new google.maps.LatLng(44.715928, -89.649161);
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
stateDistricts["dc"] = 0
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

var zoomLevel = [];
zoomLevel["al"] = 7;
zoomLevel["ak"] = 5;
zoomLevel["az"] = 7;
zoomLevel["ar"] = 7;
zoomLevel["ca"] = 6;
zoomLevel["co"] = 7;
zoomLevel["ct"] = 9;
zoomLevel["de"] = 9
zoomLevel["dc"] = 11
zoomLevel["fl"] = 7
zoomLevel["ga"] = 7
zoomLevel["hi"] = 8
zoomLevel["id"] = 6
zoomLevel["il"] = 7
zoomLevel["in"] = 7
zoomLevel["ia"] = 7
zoomLevel["ks"] = 7
zoomLevel["ky"] = 8
zoomLevel["la"] = 7
zoomLevel["me"] = 7
zoomLevel["md"] = 8
zoomLevel["ma"] = 8
zoomLevel["mi"] = 7
zoomLevel["mn"] = 7
zoomLevel["ms"] = 7
zoomLevel["mo"] = 7
zoomLevel["mt"] = 7
zoomLevel["ne"] = 7
zoomLevel["nv"] = 6
zoomLevel["nh"] = 8
zoomLevel["nj"] = 8
zoomLevel["nm"] = 7
zoomLevel["ny"] = 7
zoomLevel["nc"] = 8
zoomLevel["nd"] = 7
zoomLevel["oh"] = 7
zoomLevel["ok"] = 7
zoomLevel["or"] = 7
zoomLevel["pa"] = 8
zoomLevel["ri"] = 10
zoomLevel["sc"] = 8
zoomLevel["sd"] = 7
zoomLevel["tn"] = 8
zoomLevel["tx"] = 6
zoomLevel["ut"] = 7
zoomLevel["vt"] = 8
zoomLevel["va"] = 8
zoomLevel["wa"] = 7
zoomLevel["wv"] = 8
zoomLevel["wi"] = 7
zoomLevel["wy"] = 7

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