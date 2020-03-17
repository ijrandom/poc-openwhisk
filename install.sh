#!/bin/bash

snap install microk8s --classic
microk8s.status enable ingress
microk8s.status enable storage
microk8s.status enable helm3
microk8s.enable dns
iptables -P FORWARD ACCEPT


git clone git@github.com:apache/openwhisk-deploy-kube.git


microk8s.kubectl label nodes --all openwhisk-role=invoker
microk8s.helm3 install ow2 ~/openwhisk-deploy-kube/helm/openwhisk/ -n openwhisk
wget 'https://github.com/apache/openwhisk-cli/releases/download/1.0.0/OpenWhisk_CLI-1.0.0-linux-amd64.tgz'
tar -xf OpenWhisk_CLI-1.0.0-linux-amd64.tgz

./wsk property set --apihost localhost:31001
./wsk property set --auth 23bc46b1-71f6-4ed5-8c54-816aa4f8c502:123zO3xZCLrMN6v2BKK1dXYFpXlPkccOFqm12CdAsMgRU4VrNZ9lyGVCGuMDGIwPq