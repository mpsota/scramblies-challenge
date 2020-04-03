(ns scrablies-challenge.core
  (:require [environ.core :refer [env]]
            [taoensso.timbre :as log :refer [debug]]
            [scrablies-challenge.systems :refer [dev-system]]
            [system.repl :refer [set-init! start system]]))

(defn -main [& args]
  (set-init! #'dev-system)
  (start)
  (log/info (format "Server started at: %s:%s" (env :api-host) (env :api-port)))
  )