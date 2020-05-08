<%--
  Created by IntelliJ IDEA.
  User: Yang
  Date: 2020/4/17
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <script src="https://unpkg.com/gojs/release/go.js"></script>
    <script>
      function init() {
        if (window.goSamples) goSamples();

        var $ = go.GraphObject.make;

        myDiagram = $(go.Diagram, "myDiagramDiv",
                {
                  "undoManager.isEnabled": true
                });


        myDiagram.nodeTemplate =
                $(go.Node, "Auto",
                        $(go.Shape, "RoundedRectangle", { strokeWidth: 0, fill: "white" },

                                new go.Binding("fill", "color")),
                        $(go.TextBlock,
                                { margin: 8, font: "bold 14px sans-serif", stroke: '#333' },

                                new go.Binding("text", "key"))
                );

        myDiagram.model = new go.GraphLinksModel(
                [
                  { key: "Alpha", color: "lightblue" },
                  { key: "Beta", color: "orange" },
                  { key: "Gamma", color: "lightgreen" },
                  { key: "Delta", color: "pink" }
                ],
                [
                  { from: "Alpha", to: "Beta" },
                  { from: "Alpha", to: "Gamma" },
                  { from: "Beta", to: "Beta" },
                  { from: "Gamma", to: "Delta" },
                  { from: "Delta", to: "Alpha" }
                ]);
      }
    </script>
  </head>
  <body onload="init()">
  <div id="myDiagramDiv"  style="width:700px; height:150px;border:1px solid black;"></div>
  </body>
</html>
