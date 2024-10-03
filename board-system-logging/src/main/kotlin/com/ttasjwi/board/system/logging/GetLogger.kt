package com.ttasjwi.board.system.logging

import com.ttasjwi.board.system.logging.impl.DelegateLogger

fun getLogger(clazz: Class<*>): Logger = DelegateLogger(clazz)
