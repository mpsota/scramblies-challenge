(ns scrablies-challenge.view
  (:require
    [reagent.core :as r]
    [cljs.pprint :refer [pprint]]
    ["semantic-ui-react" :refer [Grid Grid.Row Grid.Column Divider Container
                                 Segment Checkbox
                                 Message Message.Header Header
                                 Button Form Form.Input Form.Button Form.Group Form.Select]]
    [ajax.core :as ajax]
    [reagent.dom :as rdom]))

;; state
(defonce send-on-change (r/atom true))
(defonce str1 (r/atom "cedewaraaossoqqyt"))
(defonce str2 (r/atom "codewars"))
(defonce result (r/atom {:result true}))

(defn scramble
  "Verify if str1,str2 are scramblable by asking backend.
  For simplicity we just use atoms instead of Redux store here"
  [_e]
  (ajax/GET "http://localhost:3001/api/scramble"
            {:params          {:available-letters @str1
                               :word-string       @str2}
             :handler         #(reset! result %)
             :error-handler   #(reset! result {:error %})
             :response-format :json
             :keywords?       true}))

(defn make-header-sub-row
  "Creates row with Header `title` of size `size` with body `text` (each string creates separate line"
  [size title & text]
  [:> Grid.Row {:columns 1}
   [:> Grid.Column
    [:> Header {:as size} title]
    [:p (interpose [:br] text)]]])

(defn form
  "Input form, when on the fly checkbox is not selected user needs to click the button,
  otherwise onChange event is used to call API"
  []
  [:> Form
   [:> Form.Group {:widths "equal"}
    [:> Form.Input {:label    "str1 - available letters"
                    :value    @str1
                    :onChange (fn [_e v]
                                (reset! str1 (.-value v))
                                (when @send-on-change
                                  (scramble nil)))}]
    [:> Form.Input {:label    "str2 - word to check"
                    :value    @str2
                    :onChange (fn [_e v]
                                (reset! str2 (.-value v))
                                (when @send-on-change
                                  (scramble nil)))}]]
   [:> Checkbox {:label    "on the fly"
                 :onChange (fn []
                             (reset! send-on-change (not @send-on-change)))
                 :checked  @send-on-change}]
   (when-not @send-on-change
     [:> Form.Group {:widths "equal"}
      [:> Button {:onClick scramble} "Scramble!"]])])

(defn index
  "Main page"
  []
  [:> Grid {:columns  1
            :centered true
            :style    {:width      "900px"
                       :text-align "center"
                       :margin     "auto"}}
   [:> Grid.Row]
   [:> Grid.Row
    [:> Grid.Column
     [:> Container
      [:> Header {:as "h1"} "Scrablies Challenge"]
      [:p {} "Web application to check if subset of letters in first string, can be rearranged to match the second."]]]]
   [make-header-sub-row "h2" "Scramblable?"
    "1) Input available letters and word you want to test"
    "2) You can optionally check \"on the fly\" to verify word on the fly."]
   [:> Grid.Row
    [:> Grid.Column {}
     [form]]]
   [make-header-sub-row "h2" "Result"]
   [:> Grid.Row
    [:> Grid.Column {}
     (js/console.log @result)
     (let [{:keys [result error]} @result]
       (cond
         error [:> Message {:color "red"}
                [:> Message.Header "Error"]
                [:p "Only lower case letters are allowed"]]
         (nil? result) nil
         result [:> Message {:color "green"}
                 [:> Message.Header "True"]
                 [:p "Scramblable - portion of str1 characters can be rearranged to match str2"]]
         :else [:> Message {:color "orange"}
                [:> Message.Header "False"]
                [:p "Not scramblable - no portion of str1 characters can be rearranged to match str2"]]))]]])