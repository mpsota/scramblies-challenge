(ns scrablies-challenge.systems
  (:require [liberator.dev :refer [wrap-trace]]
            [environ.core :refer [env]]
            [system.core :refer [defsystem]]
            [com.stuartsierra.component :as component]
            (system.components
              [jetty :refer [new-jetty]]
              [endpoint :refer [new-endpoint]]
              [middleware :refer [new-middleware]]
              [handler :refer [new-handler]]
              [repl-server :refer [new-repl-server]])
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.cors :refer [wrap-cors]]
            [scrablies-challenge.api :as api]))

(declare dev-system)

;;; For simplicity we use dev-system for development, production and tests

(defsystem dev-system
  [:routes (new-endpoint api/app-routes)
   :middleware (new-middleware {:middleware [wrap-restful-format
                                             [wrap-trace :ui]
                                             [wrap-defaults api-defaults]
                                             [wrap-cors
                                              :access-control-allow-origin [(re-pattern (env :allow-origin))]
                                              :access-control-allow-credentials "true"
                                              :access-control-allow-methods [:get :put :post :delete]]]})
   :handler (-> (new-handler)
                (component/using [:routes :middleware]))
   :http (-> (new-jetty :port (Integer/parseUnsignedInt (env :api-port)))
             (component/using [:handler]))])