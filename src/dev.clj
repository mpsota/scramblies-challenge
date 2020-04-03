(ns dev
  (:require [scrablies-challenge.systems :refer [dev-system]]
            [system.repl :refer [reset set-init! start stop system]]))

(set-init! #'dev-system)
