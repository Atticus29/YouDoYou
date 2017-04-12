import React, {StyleSheet, Dimensions, PixelRatio} from "react-native";
const {width, height, scale} = Dimensions.get("window"),
    vw = width / 100,
    vh = height / 100,
    vmin = Math.min(vw, vh),
    vmax = Math.max(vw, vh);

export default StyleSheet.create({
    "[class*=\"entypo-\"]:before": {
        "fontFamily": "'entypo', sans-serif"
    },
    "*": {
        "boxSizing": "border-box"
    },
    "body": {
        "background": "#f5f5f5",
        "maxWidth": 1200,
        "marginTop": 0,
        "marginRight": "auto",
        "marginBottom": 0,
        "marginLeft": "auto",
        "paddingTop": 10,
        "paddingRight": 10,
        "paddingBottom": 10,
        "paddingLeft": 10,
        "fontFamily": "'Lato', sans-serif",
        "textShadow": "0 0 1px rgba(255, 255, 255, 0.004)",
        "fontSize": "100%",
        "fontWeight": "400"
    },
    "toggler": {
        "color": "#A1A1A4",
        "fontSize": 1.25,
        "marginLeft": 8,
        "textAlign": "center",
        "cursor": "pointer"
    },
    "toggleractive": {
        "color": "#000"
    },
    "surveys": {
        "listStyle": "none",
        "marginTop": 0,
        "marginRight": 0,
        "marginBottom": 0,
        "marginLeft": 0,
        "paddingTop": 0,
        "paddingRight": 0,
        "paddingBottom": 0,
        "paddingLeft": 0
    },
    "survey-item": {
        "display": "block",
        "marginTop": 10,
        "paddingTop": 20,
        "paddingRight": 20,
        "paddingBottom": 20,
        "paddingLeft": 20,
        "borderRadius": 2,
        "background": "white",
        "boxShadow": "0 2px 1px rgba(170, 170, 170, 0.25)"
    },
    "survey-name": {
        "fontWeight": "400"
    },
    "list survey-item": {
        "position": "relative",
        "paddingTop": 0,
        "paddingRight": 0,
        "paddingBottom": 0,
        "paddingLeft": 0,
        "fontSize": 14,
        "lineHeight": 40
    },
    "list survey-item pull-right": {
        "position": "absolute",
        "right": 0,
        "top": 0
    },
    "list survey-country": {
        "color": "#A1A1A4",
        "marginTop": 0,
        "marginRight": 0,
        "marginBottom": 0,
        "marginLeft": 10,
        "verticalAlign": "middle"
    },
    "list survey-progress": {
        "color": "#A1A1A4"
    },
    "list survey-completes": {
        "color": "#A1A1A4",
        "marginTop": 0,
        "marginRight": 10,
        "marginBottom": 0,
        "marginLeft": 10,
        "verticalAlign": "middle"
    },
    "list survey-end-date": {
        "color": "#A1A1A4",
        "marginTop": 0,
        "marginRight": 10,
        "marginBottom": 0,
        "marginLeft": 10,
        "verticalAlign": "middle",
        "display": "inline-block",
        "width": 100,
        "whiteSpace": "nowrap",
        "overflow": "hidden"
    },
    "list survey-name": {
        "marginTop": 0,
        "marginRight": 10,
        "marginBottom": 0,
        "marginLeft": 10,
        "verticalAlign": "middle"
    },
    "list survey-stage": {
        "marginTop": 0,
        "marginRight": 10,
        "marginBottom": 0,
        "marginLeft": 10
    },
    "survey-stage stage": {
        "display": "inline-block",
        "verticalAlign": "middle",
        "width": 16,
        "height": 16,
        "overflow": "hidden",
        "borderRadius": "50%",
        "paddingTop": 0,
        "paddingRight": 0,
        "paddingBottom": 0,
        "paddingLeft": 0,
        "marginTop": 0,
        "marginRight": 2,
        "marginBottom": 0,
        "marginLeft": 2,
        "background": "#f2f2f2",
        "textIndent": -9999,
        "color": "transparent",
        "lineHeight": 16
    },
    "survey-stage stageactive": {
        "background": "#A1A1A4"
    },
    "list list-only": {
        "display": "auto"
    },
    "list grid-only": {
        "display": "none !important"
    },
    "grid grid-only": {
        "display": "auto"
    },
    "grid list-only": {
        "display": "none !important"
    },
    "grid survey-item": {
        "position": "relative",
        "display": "inline-block",
        "verticalAlign": "top",
        "height": 200,
        "width": 250,
        "marginTop": 10,
        "marginRight": 10,
        "marginBottom": 10,
        "marginLeft": 10
    },
    "grid survey-name": {
        "display": "block",
        "maxWidth": "80%",
        "fontSize": 16,
        "lineHeight": 20
    },
    "grid survey-country": {
        "fontSize": 11,
        "lineHeight": 16,
        "textTransform": "uppercase",
        "color": "#A1A1A4"
    },
    "grid survey-end-date": {
        "color": "#A1A1A4",
        "fontSize": 12,
        "lineHeight": 20
    },
    "grid survey-end-date:before": {
        "content": "'Ends\\00a0'"
    },
    "grid survey-end-dateended:before": {
        "content": "'Ended\\00a0'"
    },
    "grid survey-progress": {
        "display": "block",
        "position": "absolute",
        "bottom": 0,
        "left": 0,
        "right": 0,
        "width": "100%",
        "paddingTop": 20,
        "paddingRight": 20,
        "paddingBottom": 20,
        "paddingLeft": 20,
        "borderTop": "1px solid #eee",
        "fontSize": 13
    },
    "grid survey-progress-bg": {
        "width": "40%",
        "display": "block"
    },
    "grid survey-progress-labels": {
        "position": "absolute",
        "right": 20,
        "top": 0,
        "lineHeight": 40
    },
    "grid survey-progress-label": {
        "lineHeight": 21,
        "fontSize": 13,
        "fontWeight": "400"
    },
    "grid survey-completes": {
        "lineHeight": 21,
        "fontSize": 13,
        "verticalAlign": "middle"
    },
    "grid survey-stage": {
        "position": "absolute",
        "top": 20,
        "right": 20
    },
    "grid survey-stage stage": {
        "display": "none"
    },
    "grid survey-stage active": {
        "display": "block"
    },
    "survey-progress-label": {
        "verticalAlign": "middle",
        "marginTop": 0,
        "marginRight": 10,
        "marginBottom": 0,
        "marginLeft": 10,
        "color": "#8DC63F"
    },
    "survey-progress-bg": {
        "display": "inline-block",
        "verticalAlign": "middle",
        "position": "relative",
        "width": 100,
        "height": 4,
        "borderRadius": 2,
        "overflow": "hidden",
        "background": "#eee"
    },
    "survey-progress-fg": {
        "position": "absolute",
        "top": 0,
        "bottom": 0,
        "height": "100%",
        "left": 0,
        "marginTop": 0,
        "marginRight": 0,
        "marginBottom": 0,
        "marginLeft": 0,
        "background": "#8DC63F"
    }
});