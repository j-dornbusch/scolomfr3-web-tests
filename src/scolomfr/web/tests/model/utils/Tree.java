package scolomfr.web.tests.model.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe utilitaire, structure d'arbre
 * 
 * @author joachim
 *
 * @param <T>
 */
public class Tree<T> {
	private Node<T> root;

	public Tree(T rootData) {
		root = new Node<T>();
		root.setdata(rootData);
		root.setChildren(new ArrayList<Node<T>>());
	}

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public static class Node<T> {
		private T data;
		private Node<T> parent;
		private List<Node<T>> children = new LinkedList<>();

		public T getData() {
			return data;
		}

		public void setdata(T data) {
			this.data = data;
		}

		public List<Node<T>> getChildren() {
			return children;
		}

		public void setChildren(List<Node<T>> children) {
			this.children = children;
		}

		public void addChild(Node<T> child) {
			this.children.add(child);
		}

		public Node<T> getParent() {
			return parent;
		}

		public void setParent(Node<T> parent) {
			this.parent = parent;
		}

		public void removeChild(T data) {
			Iterator<Node<T>> it = children.iterator();
			while (it.hasNext()) {
				Tree.Node<T> node = (Tree.Node<T>) it.next();
				if (node.getData().equals(data)) {
					children.remove(node);
					break;
				}
			}

		}
	}
}
