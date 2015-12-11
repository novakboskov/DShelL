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
     ~@body))

;;;; Macros for meta

(with-implementation ::batch
  (script
   (println "HOME")))

;;;;

;;
;; Testing concepts
;;

;; Interning functions in another namespaces

;; Problems:
;; - How to provide an option of changing implementation of make-a-function?
;; - How to make dynamical generated functions available in development
;; of generated or hand written kode?

(defmacro make-a-function
  ([param1 param2 & body]
   `(fn [~param1 ~param2 ~body]
      (println (+ 2 ~param1 ~param2))
      ~@body))
  ([param1 param2]
   `(fn [~param1 ~param2]
      (+ 2 ~param1 ~param2))))

(create-ns 'app.models.foodstuffs)
(binding [*ns* 'app.models.foodstuffs]
  (intern 'app.models.foodstuffs 'some-foo (make-a-function p1 p2)))

(app.models.foodstuffs/some-foo 2 4)

;; Generating code in files
(str (macroexpand '(make-a-function first-p second-p)))
