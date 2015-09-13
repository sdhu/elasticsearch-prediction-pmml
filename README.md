Search Prediction Using PMML Models
======================

Using the api from [elasticsearch-prediction](https://github.com/mahisoft/elasticsearch-prediction), this is the implementation only implements the Predictor engine for a PMML model that has been trained in any other model that has been serialized to PMML, either in R, python, Spark, etc...

*This is highly experimental code as a proof of concept, so there are many many areas of improvements, and bugs. Use for fun only*

Note that currently the only suppported Double type until a more robust library is built

Some references:
- [PMML Specification](http://www.dmg.org/v4-2-1/GeneralStructure.html)
- [JPMML](https://github.com/jpmml)
- [scripting-scores](http://www.elasticsearch.org/guide/en/elasticsearch/guide/current/script-score.html)
- [modules-scripting](http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/modules-scripting.html)

## License
Code is provided under the Apache 2.0 license available at http://opensource.org/licenses/Apache-2.0,
as well as in the LICENSE file. This is the same license used as [elasticsearch-prediction](https://github.com/mahisoft/elasticsearch-prediction); howver, JPMML-Model is licensed under the [BSD 3-Clause License](http://opensource.org/licenses/BSD-3-Clause)
