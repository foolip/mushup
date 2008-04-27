from __future__ import with_statement

import jpype
from neo4j import *

org = jpype.JPackage('org')  

neo = EmbeddedNeo(org.foolip.mushup.Relationships, "nodestore", True)
with Transaction():
    node = neo.getStartNode()  # work with default node space (if created before)
    node = neo.createNode()    # create a new node
    node = neo.getNodeById(id) # access a specific node
