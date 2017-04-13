import React, {StyleSheet, Dimensions, PixelRatio} from "react-native";
const {width, height, scale} = Dimensions.get("window"),
    vw = width / 100,
    vh = height / 100,
    vmin = Math.min(vw, vh),
    vmax = Math.max(vw, vh);

export default StyleSheet.create({
    "h1": {
        "fontSize": 30,
        "color": "#fff",
        "textTransform": "uppercase",
        "fontWeight": "300",
        "textAlign": "center",
        "marginBottom": 15
    },
    "table": {
        "width": "100%",
        "tableLayout": "fixed"
    },
    "tbl-header": {
        "backgroundColor": "rgba(255,255,255,0.3)"
    },
    "tbl-content": {
        "height": 300,
        "overflowX": "auto",
        "marginTop": 0,
        "border": "1px solid rgba(255,255,255,0.3)"
    },
    "th": {
        "paddingTop": 20,
        "paddingRight": 15,
        "paddingBottom": 20,
        "paddingLeft": 15,
        "textAlign": "left",
        "fontWeight": "500",
        "fontSize": 12,
        "color": "#fff",
        "textTransform": "uppercase"
    },
    "td": {
        "paddingTop": 15,
        "paddingRight": 15,
        "paddingBottom": 15,
        "paddingLeft": 15,
        "textAlign": "left",
        "verticalAlign": "middle",
        "fontWeight": "300",
        "fontSize": 12,
        "color": "#fff",
        "borderBottom": "solid 1px rgba(255,255,255,0.1)"
    },
    "body": {
        "background": "linear-gradient(to right, #25c481, #25b7c4)",
        "fontFamily": "'Roboto', sans-serif"
    },
    "section": {
        "marginTop": 50,
        "marginRight": 50,
        "marginBottom": 50,
        "marginLeft": 50
    },
    "::-webkit-scrollbar": {
        "width": 6
    },
    "::-webkit-scrollbar-track": {
        "WebkitBoxShadow": "inset 0 0 6px rgba(0,0,0,0.3)"
    },
    "::-webkit-scrollbar-thumb": {
        "WebkitBoxShadow": "inset 0 0 6px rgba(0,0,0,0.3)"
    }
});