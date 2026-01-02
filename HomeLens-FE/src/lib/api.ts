import { Flat, Review, Society } from '@/types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

type ReviewResponse = Omit<Review, 'createdAt' | 'updatedAt'> & {
  createdAt: string;
  updatedAt: string;
};

async function fetchJson<T>(path: string): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`);
  if (!response.ok) {
    throw new Error(`Request failed: ${response.status}`);
  }
  return response.json() as Promise<T>;
}

function normalizeReview(review: ReviewResponse): Review {
  return {
    ...review,
    createdAt: new Date(review.createdAt),
    updatedAt: new Date(review.updatedAt),
  };
}

export async function fetchSocieties(): Promise<Society[]> {
  return fetchJson<Society[]>('/api/societies');
}

export async function fetchSociety(id: string): Promise<Society> {
  return fetchJson<Society>(`/api/societies/${id}`);
}

export async function fetchFlatsBySociety(id: string): Promise<Flat[]> {
  return fetchJson<Flat[]>(`/api/societies/${id}/flats`);
}

export async function fetchFlat(id: string): Promise<Flat> {
  return fetchJson<Flat>(`/api/flats/${id}`);
}

export async function fetchReviewsByFlat(id: string): Promise<Review[]> {
  const data = await fetchJson<ReviewResponse[]>(`/api/flats/${id}/reviews`);
  return data.map(normalizeReview);
}

export async function fetchReviewsByUser(userId: string): Promise<Review[]> {
  const data = await fetchJson<ReviewResponse[]>(`/api/users/${userId}/reviews`);
  return data.map(normalizeReview);
}
