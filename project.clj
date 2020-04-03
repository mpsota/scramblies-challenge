(defproject scrablies-challenge "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/test.check "1.0.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.danielsz/system "0.4.1"]
                 [clj-http "3.10.0"]
                 [cheshire "5.9.0"]                         ;; for :as :json in clj-http
                 [environ "1.1.0"]
                 [compojure "1.6.1"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring-middleware-format "0.7.3"]
                 [ring/ring-defaults "0.3.2"]
                 [ring-cors "0.1.12"]
                 [ring/ring-mock "0.4.0"]                   ;; testing
                 [liberator "0.15.2"]
                 [thheller/shadow-cljs "2.8.90"]
                 [reagent "0.8.1"]
                 [cljs-ajax "0.8.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot scrablies-challenge.core
  :repl-options {:init-ns dev}
  :profiles {:uberjar       {:aot :all}
             :dev           [:project/dev :profiles/dev]
             :test          [:project/test :profiles/test]
             :profiles/dev  {}
             :profiles/test {}
             :project/dev   {:aot :all
                             :env {:api-host     "http://localhost"
                                   :api-port     "3001"
                                   :allow-origin ".*"
                                   }}
             :project/test  {:aot :all
                             :env {:api-host     "http://localhost"
                                   :api-port     "4001"
                                   :allow-origin ".*"
                                   }}})
