import React, {StyleSheet, Dimensions, PixelRatio} from "react-native";
const {width, height, scale} = Dimensions.get("window"),
    vw = width / 100,
    vh = height / 100,
    vmin = Math.min(vw, vh),
    vmax = Math.max(vw, vh);

export default StyleSheet.create({
    "*": {
        "WebkitBoxSizing": "border-box",
        "MozBoxSizing": "border-box",
        "OBoxSizing": "border-box",
        "boxSizing": "border-box",
        "WebkitTransition": ".25s ease-in-out",
        "MozTransition": ".25s ease-in-out",
        "OTransition": ".25s ease-in-out",
        "transition": ".25s ease-in-out",
        "marginTop": 0,
        "marginRight": 0,
        "marginBottom": 0,
        "marginLeft": 0,
        "paddingTop": 0,
        "paddingRight": 0,
        "paddingBottom": 0,
        "paddingLeft": 0,
        "WebkitTextSizeAdjust": "none"
    },
    "html": {
        "height": "100%",
        "overflow": "hidden"
    },
    "body": {
        "height": "100%",
        "overflow": "hidden"
    },
    "drawer-toggle": {
        "position": "absolute",
        "opacity": 0
    },
    "drawer-toggle-label": {
        "WebkitTouchCallout": "none",
        "WebkitUserSelect": "none",
        "KhtmlUserSelect": "none",
        "MozUserSelect": "none",
        "MsUserSelect": "none",
        "userSelect": "none",
        "left": 0,
        "height": 50,
        "width": 50,
        "display": "block",
        "position": "fixed",
        "background": "rgba(255,255,255,.0)",
        "zIndex": 1
    },
    "drawer-toggle-label:before": {
        "content": "''",
        "display": "block",
        "position": "absolute",
        "height": 2,
        "width": 24,
        "background": "#8d8d8d",
        "left": 13,
        "top": 18,
        "boxShadow": "0 6px 0 #8d8d8d, 0 12px 0 #8d8d8d"
    },
    "header": {
        "width": "100%",
        "position": "fixed",
        "left": 0,
        "background": "#efefef",
        "paddingTop": 10,
        "paddingRight": 10,
        "paddingBottom": 10,
        "paddingLeft": 50,
        "fontSize": 30,
        "lineHeight": 30,
        "zIndex": 0
    },
    "drawer": {
        "position": "fixed",
        "top": 0,
        "left": -300,
        "height": "100%",
        "width": 300,
        "background": "#2f2f2f",
        "overflowX": "hidden",
        "overflowY": "scroll",
        "paddingTop": 20,
        "paddingRight": 20,
        "paddingBottom": 20,
        "paddingLeft": 20,
        "WebkitOverflowScrolling": "touch"
    },
    "page-content": {
        "marginLeft": 0,
        "marginTop": 50,
        "width": "100%",
        "height": "calc(100% - 50px)",
        "overflowX": "hidden",
        "overflowY": "scroll",
        "WebkitOverflowScrolling": "touch",
        "paddingTop": 20,
        "paddingRight": 20,
        "paddingBottom": 20,
        "paddingLeft": 20
    },
    "drawer-toggle:checked ~ drawer-toggle-label": {
        "height": "100%",
        "width": "calc(100% - 300px)",
        "background": "rgba(255,255,255,.8)",
        "left": 300
    },
    "drawer-toggle:checked ~ header": {
        "left": 300
    },
    "drawer-toggle:checked ~ drawer": {
        "left": 0
    },
    "drawer-toggle:checked ~ page-content": {
        "marginLeft": 300
    },
    "drawer ul": {
        "listStyleType": "none"
    },
    "drawer ul a": {
        "display": "block",
        "paddingTop": 10,
        "paddingRight": 10,
        "paddingBottom": 10,
        "paddingLeft": 10,
        "color": "#c7c7c7",
        "textDecoration": "none"
    },
    "drawer ul a:hover": {
        "color": "white"
    }
});