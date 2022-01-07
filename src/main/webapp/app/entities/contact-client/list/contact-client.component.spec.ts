import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ContactClientService } from '../service/contact-client.service';

import { ContactClientComponent } from './contact-client.component';

describe('ContactClient Management Component', () => {
  let comp: ContactClientComponent;
  let fixture: ComponentFixture<ContactClientComponent>;
  let service: ContactClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ContactClientComponent],
    })
      .overrideTemplate(ContactClientComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactClientComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ContactClientService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.contactClients?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
