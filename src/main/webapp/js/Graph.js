function GraphData(image, color, minValue, midValue, maxValue) {
    this.image = image;
    this.color = color;
    this.minValue = minValue;
    this.midValue = midValue;
    this.maxValue = maxValue;
}


function Graph(minContainer, midContainer, maxContainer) {
    this.data = new Array();
    this.minContainer = minContainer;
    this.midContainer = midContainer;
    this.maxContainer = maxContainer;
}

Graph.prototype.show = function(graph, flag) {
    var img;

    img = this.data[graph].image;
    img.style.visibility = flag ? "visible" : "hidden";

    this.refresh();
};

Graph.prototype.add = function(name, graphData) {
    this.data[name] = graphData;
};

Graph.prototype.refresh = function() {
    var min = "";
    var mid = "";
    var max = "";
    var data;

    for (var name in this.data) {
        data = this.data[name];

        if (data.image.style.visibility != "hidden") {
            if (data.minValue)
            min += "<div style=\"color: " + data.color + "\">" + data.minValue + "</div>";
            mid += "<div style=\"color: " + data.color + "\">" + data.midValue + "</div>";
            max += "<div style=\"color: " + data.color + "\">" + data.maxValue + "</div>";
        }
    }

    this.minContainer.innerHTML = min;
    this.midContainer.innerHTML = mid;
    this.maxContainer.innerHTML = max;
};


