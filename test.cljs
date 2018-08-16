(require '[cljs.test :refer-macros [deftest is testing run-tests async]])
(def pup (js/require "puppeteer"))

(deftest test-numbers
  (async done
    (-> (.launch pup)
        (.then (fn [browser]
                 (-> (.newPage browser)
                     (.then (fn [page]
                              (-> (.goto page "https://example.com/")
                                  (.then #(.screenshot page #js {:path "example.png"}))
                                  (.then #(.title page))
                                  (.then (fn [title]
                                             (is (= "Example Domain" title))
                                             (.close browser)))
                                  (.then done))))))))))

(run-tests)
