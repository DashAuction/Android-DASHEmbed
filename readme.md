# DASH

## Purpose

This library provides an easy way to embed the DASH mobile experience into an Android application though a fragment.

## Usage

### Create a Config

Create a Config. This is used to initialize the DASH library with needed information.

```java
Config config = new Config("fcdallas", "hopscotch", "io.dashapp.dashembedexample");
```
### Initialize the DASH library

Initialize library either using the shared instance.

```java
DASH.getInstance().startWithConfig(config);
```

### Display the provided DASHFragment

```java
Fragment fragment = DASH.getInstance().dashFragement();
FragmentManager fragmentManager = getFragmentManager();

FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

fragmentTransaction
.replace(R.id.main_panel, fragment, "DASHFragment")
.addToBackStack("DASHFragment")
.commit();
fragmentManager.executePendingTransactions();
```

Check out the [Current Documentation](https://bitbucket.org/dashdev/dash-embed-ios/raw/21b09d1d6620a0594e88aea498cfa46a4717b43b/Documentation/DASHAuctions_V1.pdf)
