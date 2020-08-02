#Kubernetes configuration

## Preparation

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw -Pprod verify jib:dockerBuild

You will need to push your image to a registry. If you have not done so, use the following commands to tag and push the images:

```
$ docker image tag brand raorizwaniftikhar/brand
$ docker push raorizwaniftikhar/brand
```
please change raorizwaniftikhar to your Docker Hub Repository name and replace your Repository name in brand-k8s/brand-deploment.yml
## Deployment

You can deploy all your apps by running the below bash command:

```
./kubectl-apply.sh -f
```

## Exploring your services

Use these commands to find your application's IP addresses:

```
$ kubectl get svc brand
```

## Scaling your deployments

You can scale your apps using

```
$ kubectl scale deployment <app-name> --replicas <replica-count>
```

## zero-downtime deployments

The default way to update a running app in kubernetes, is to deploy a new image tag to your docker registry and then deploy it using

```
$ kubectl set image deployment/<app-name>-app <app-name>=<new-image>
```

## Find Port no:

kubectl get svc brand

## Find IP Address

minikube ip
