package com.payby.export.demo.ui.entity

data class EmvTag(
    var tc: String ? = null,
    var aid: String ? = null,
    var atc: String ? = null,
    var tsi: String ? = null,
    var tvr: String ? = null,
    var cid: String ? = null,
    var arqc: String ? = null,
    var arpc: String ? = null,
    var appName: String ? = null,
    var appLabel: String ? = null,
)