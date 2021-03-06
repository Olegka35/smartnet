$(document).ready(function() {
   $.getJSON("/nets", function(result) {
        console.log(result);
        for(let i = 0; i < result.length; i++) {
            let id = 'net'+result[i].id;
            $("#networks").append("<p id = " + id + " class = 'network_row'>" + parseInt(parseInt(i)+1) + ". " + result[i].name + "</p>");
            $('#'+id).click(function() { displayNet(result[i].id); });
        }
   });
});

function displayNet(netID) {
    $('#netboard').html();
    getNetFromService(netID);
}

function getNetFromService(netID) {
    $.getJSON("/net/"+netID, function(result) {
        $('#elements').empty();
        $('#attr').empty();

        var elements = result.graph.vertices, connections = result.graph.connections;
        var data = [], edges = [];
        elements.forEach(function(element, i) {
            //data.push({id: element.id, label: element.id.toString()});
            var params = element.params;
            //var index = findIndexByElementId(params, 1);
            var index = 0;
            var elementName = params[index].value.toString();
            data.push({id: element.id, label: elementName });
            $('#elements').append("<input type='checkbox' />" + elementName + "<br/>");
        });
        connections.forEach(function(edge, i) {
            var title = "";
            edge.params.forEach(function(param, j) {
                var paramName = getParamName(result.graph.params, param.id);
                title = title.concat(paramName + ": " + param.value + "; ");
                $('#attr').append("<option>" + paramName + "</option>");
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
        var container = document.getElementById('netboard');

        var data = {
            nodes: nodes,
            edges: edges
        };
        var options = {};
        var network = new vis.Network(container, data, options);
    });
}

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