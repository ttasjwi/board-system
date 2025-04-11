package com.ttasjwi.board.system.common.logger

fun getLogger(clazz: Class<*>): Logger = DefaultLogger(clazz)
