(ns scrablies-challenge.api-test
  (:require [clojure.test :refer :all]
            [system.repl :refer [set-init! start stop]]
            [scrablies-challenge.systems :refer [dev-system]]
            [environ.core :refer [env]]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [taoensso.timbre :as log]))


; localhost:3001/api/scramble?available-letters=abcde&word-string=abaAh
; localhost:3001/api/scramble?available-letters=&word-string=

;; just one fixture, so we dont move it to separate file.
(defn with-server-fixture [f]
  (log/info "Initializing database")
  (set-init! #'dev-system)
  (log/debug (env :api-port))
  (start)
  (f)
  (stop))

(use-fixtures :once with-server-fixture)

(comment                                                    ;; for debug purposes
  (def search-url (format "%s:%s/%s" (env :api-host) (env :api-port) "api/scramble")))

(deftest api-test
  (let [search-url (format "%s:%s/%s" (env :api-host) (env :api-port) "api/scramble")]
    (testing "Correct Query"
      (is (= true
             (-> (client/get search-url {:query-params {:available-letters "tset"
                                                        :word-string       "test"}
                                         :accept       :json})
                 (:body)
                 (json/read-str :key-fn keyword)
                 (:result)))))

    (testing "Empty Query"
      (is (= 400
             (-> (client/get search-url {:query-params     {:available-letters ""
                                                            :word-string       ""}
                                         :accept           :json
                                         :throw-exceptions false})
                 (:status)))))))
