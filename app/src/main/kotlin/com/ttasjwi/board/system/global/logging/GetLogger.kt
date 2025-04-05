package com.ttasjwi.board.system.global.logging

import com.ttasjwi.board.system.global.logging.impl.DelegateLogger

fun getLogger(clazz: Class<*>): Logger = DelegateLogger(clazz)
