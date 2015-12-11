(ns dsl.default-namespace
  (:require [dsl.core]))

(defmethod dsl.core/full-moon-behaviour :bill-murray
  [were-creature]
  (str (:name were-creature) " will be the most likeable celebrity"))
