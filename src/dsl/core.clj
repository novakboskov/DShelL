(ns dsl.core)

(defmulti emit-bash
  (fn [form]
    (class form)))

(defmethod emit-bash
  clojure.lang.PersistentList
  [form]
  (case (name (first form))
    "println" (str "echo " (second form))
    nil))

(defmethod emit-bash
  java.lang.String
  [string]
  string)

(defmethod emit-bash
  java.lang.Integer
  [integer]
  (str integer))

(defmethod emit-bash
  java.lang.Double
  [double]
  (str double))

(defmulti emit-batch
  (fn [form]
    (class form)))

(defmethod emit-batch clojure.lang.PersistentList
  [list]
  (case (name (first list))
    "println" (str "ECHO " (second list))
    nil))

(defmethod emit-batch java.lang.String
  [string]
  string)

(defmethod emit-batch java.lang.Integer
  [integer]
  (str integer))

(defmethod emit-batch java.lang.Double
  [double]
  (str double))
