(require '[cljs.test :refer-macros [deftest is testing run-tests async]]
         '[kitchen-async.promise :as p])
(def pup (js/require "puppeteer"))

(deftest test-with-kitchen-async
  (async done
         (p/let [browser (.launch pup)
                 page (.newPage browser)]
           (.goto page "https://example.com")
           (.screenshot page #js{:path "screenshot1.png"})
           (.close browser)
           (done))))

(deftest test-with-promises
  (async done
         (-> (.launch pup)
             (.then (fn [browser]
                      (-> (.newPage browser)
                          (.then (fn [page]
                                   (-> (.goto page "https://example.com/")
                                       (.then #(.screenshot page #js {:path "screenshot2.png"}))
                                       (.then #(.title page))
                                       (.then (fn [title]
                                                (is (= "Example Domain" title))
                                                (.close browser)))
                                       (.then done))))))))))

(run-tests)
