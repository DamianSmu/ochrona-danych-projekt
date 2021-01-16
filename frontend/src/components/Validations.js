import React from "react";
import {isEmail} from "validator";

export const required = (value) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

export const validEmail = (value) => {
    if (!isEmail(value)) {
        return (
            <div className="alert alert-danger" role="alert">
                This is not a valid email.
            </div>
        );
    }
};

export const vusername = (value) => {
    if (value.length < 3 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The username must be between 3 and 20 characters.
            </div>
        );
    }
};

export const calcEntropy = (value) => {
    var a = 0, u = 0, n = 0, ns = 0, r = 0, sp = 0, chars = 0;
    for (var i = 0; i < value.length; i++) {
        var c = value.charAt(i);

        if (a === 0 && 'abcdefghijklmnopqrstuvwxyz'.indexOf(c) >= 0) {
            chars += 26;
            a = 1;
        }
        if (u === 0 && 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.indexOf(c) >= 0) {
            chars += 26;
            u = 1;
        }
        if (n === 0 && '0123456789'.indexOf(c) >= 0) {
            chars += 10;
            n = 1;
        }
        if (ns === 0 && '!@#$%^&*()'.indexOf(c) >= 0) {
            chars += 10;
            ns = 1;
        }
        if (r === 0 && "`~-_=+[{]}\\|;:'\",<.>/?".indexOf(c) >= 0) {
            chars += 22;
            r = 1;
        }
        if (sp === 0 && c === ' ') {
            chars += 1;
            sp = 1;
        }
    }
    return value.length * Math.log2(chars)
};

export const vpassword = (value) => {
    var entropy = calcEntropy(value)
    if (entropy < 70) {
        return (
            <div className="alert alert-danger" role="alert">
                Password is weak.
                <br/>
                Entropy: {entropy.toFixed(0)} bits.
            </div>
        );
    }
};

export default {
    required,
    validEmail,
    vusername,
    calcEntropy
};

