$(document).ready(function() {
   $.getJSON("/net/1", function(result) {
        console.log(result);
        var elements = result.graph.vertices, connections = result.graph.connections;
        var data = [], edges = [];
        elements.forEach(function(element, i) {
            //data.push({id: element.id, label: element.id.toString()});
            var params = element.params;
            var index = findIndexByElementId(params, 1);
            data.push({id: element.id, label: params[index].value.toString()});
        });
        connections.forEach(function(edge, i) {
            var title = "";
            edge.params.forEach(function(param, j) {
                title = title.concat(getParamName(result.graph.params, param.id) + ": " + param.value + "; ");
            });
            if(edge.reverse == true) {
                if(findEdge(edges, edge.from, edge.to) == false)
                    edges.push({from: edge.from, to: edge.to, arrows: 'to, from', title: title});
            }
            else
                edges.push({from: edge.from, to: edge.to, arrows: 'to'});
        });
        var nodes = new vis.DataSet(data);
        var edges = new vis.DataSet(edges);
        var container = document.getElementById('mynetwork');

        var data = {
            nodes: nodes,
            edges: edges
        };
        var options = {};
        var network = new vis.Network(container, data, options);
   });
});

function findIndexByElementId(arr, id) {
    for(let i = 0; i < arr.length; ++i) {
        if(arr[i].id == id)
            return i;
    }
    return -1;
}

function findEdge(edges, from, to) {
    for(let i = 0; i < edges.length; ++i) {
        if((edges[i].from == from && edges[i].to == to) || (edges[i].from == to && edges[i].to == from))
            return true;
    }
    return false;
}

function getParamName(params, id) {
    for(let i = 0; i < params.length; ++i) {
        if(params[i].id == id)
            return params[i].name;
    }
}