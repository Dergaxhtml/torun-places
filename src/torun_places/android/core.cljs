(ns torun-places.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [torun-places.events]
            [torun-places.subs]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
      (.alert (.-Alert ReactNative) title))



(defn app-root []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
       [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} @greeting]
       [image {:source logo-img
               :style  {:width 80 :height 80 :margin-bottom 30}}]
       [touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5}
                             :on-press #(alert "HELLO!")}
        [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]])))

;(defonce selected-views (atom "view!"))


;(defn app-root []
;      [:div "hello world"])

;(defn app-root []
;      (let [list[:div#listViews-wrapper
;       [:ul.listViews-nav "Places"
;        [:li [:a {:on-click #(alert= "Old City View") } "OldCityView"]]
;        [:li [:a {:on-click #(alert= "Airport") } "Airport"]]
;        [:li [:a {:on-click #(alter= "Port Drzewny") } "PortDrzewny"]]
;        [:li [:a {:on-click #(alter= "Kadr") } "Kadr"]]
;        [:li [:a {:on-click #(alter= "NRD") } "NRD"]]]
;       [:label ]]]))
;
;      (reagent/render-component [listViews] (.getElementById js/document "app"))


;defn app-root []
;(let  listViews []
;      [:div#listViews-wrapper
;       [:ul.listViews-nav
;        [:li.sidebar-brand [:a {:href "#"} "Places"]]
;        [:li [:a {:on-click #(reset! selected-views"OldCityView" alert="Old City View") :href "#"} "OldCityView"]]
;        [:li [:a {:on-click #(reset! selected-views "AirPort" alert= "Airport") :href "#"} "Airport"]]
;        [:li [:a {:on-click #(reset! selected-views"PortDrzewny" alter= "Port Drzewny") :href "#"} "PortDrzewny"]]
;        [:li [:a {:on-click #(reset! selected-views"Kadr" alter= "Kadr") :href "#"} "Kadr"]]
;        [:li [:a {:on-click #(reset! selected-views"NRD" alter= "NRD") :href "#"} "NRD"]]]
;       [:label @selected-views]]))
;
;;(reagent/render-component [listViews] (.getElementById js/document "app"))

      (defn init []
      (dispatch-sync [:initialize-db])
      (.registerComponent app-registry "TorunPlaces" #(r/reactify-component app-root)))
