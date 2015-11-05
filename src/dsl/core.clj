(ns dsl.core)

(derive ::bash ::common)
(derive ::batch ::common)

(def ^:dynamic *current-implementation*
  "The current script language implementation to generate"
  ::common)

(defmulti emit
  (fn [form]
    [*current-implementation* (class form)]))

(defmethod emit [::common java.lang.String]
  [form]
  form)

(defmethod emit [::common java.lang.Integer]
  [form]
  (str form))

(defmethod emit [::common java.lang.Double]
  [form]
  (str form))

(defmethod emit [::bash clojure.lang.PersistentList]
  [form]
  (case (name (first form))
    "println" (str "echo " (second form))
    nil))

(defmethod emit [::batch clojure.lang.PersistentList]
  [form]
  (case (name (first form))
    "println" (str "ECHO " (second form))
    nil))

(defmacro script [form]
  `(emit '~form))

(defmacro with-implementation
  [impl & body]
  `(binding [*current-implementation* ~impl]
     ~@body)))
