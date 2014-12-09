var currentState;

var spinnerOpts = {
  lines: 13, // The number of lines to draw
  length: 17, // The length of each line
  width: 8, // The line thickness
  radius: 21, // The radius of the inner circle
  corners: 1, // Corner roundness (0..1)
  rotate: 58, // The rotation offset
  direction: 1, // 1: clockwise, -1: counterclockwise
  color: '#787878', // #rgb or #rrggbb or array of colors
  speed: 0.9, // Rounds per second
  trail: 100, // Afterglow percentage
  shadow: false, // Whether to render a shadow
  hwaccel: false, // Whether to use hardware acceleration
  className: 'spinner', // The CSS class to assign to the spinner
  zIndex: 2e9, // The z-index (defaults to 2000000000)
  top: '50%', // Top position relative to parent
  left: '50%' // Left position relative to parent
};

$(document).ready(function() {
  google.maps.event.addDomListener(window, 'load', initialize(defaultState, stateDistricts[defaultState]));
  
    var stateMenu = document.getElementById('state-menu');
    var boundaryCheckbox = document.getElementById('boundary-checkbox');
    
    stateMenu.value = defaultState;
    showBoundaries = $('#boundary-checkbox:checked').val();
    currentState = defaultState;
    
    boundaryCheckbox.addEventListener("change", function() {
        initialize(currentState);
    });
});

function initialize(currentState) {
  var spinner = new Spinner(spinnerOpts).spin(document.getElementById('map-menu'));
  var numDistricts = stateDistricts[currentState];
  
  if ($("#boundary-checkbox").is(":checked")) {
    sOpacity = 1.0;
  } else {
    sOpacity = 0.0;
  }

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
            strokeColor: 'black',
            strokeOpacity: sOpacity,
            strokeWeight: 1,
            fillColor: color,
            fillOpacity: opacity
          });
        }
      }
      
      spinner.stop();
    }
  });
}