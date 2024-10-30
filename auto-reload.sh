#!/usr/bin/env bash
# Nunca me acuerdo como usar entr...
# Reiniciar proyecto cuando hay cambios en los archivos
fd ".java|.hbs|.css" | entr -r mvn clean compile exec:java